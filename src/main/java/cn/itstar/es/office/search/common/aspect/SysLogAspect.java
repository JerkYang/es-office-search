package cn.itstar.es.office.search.common.aspect;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import cn.itstar.es.office.search.common.annotation.SysLog;
import cn.itstar.es.office.search.common.constant.SystemConstant;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.common.entity.SysLogEntity;
import cn.itstar.es.office.search.common.manager.SysLogManager;
import cn.itstar.es.office.search.common.utils.IpUtils;
import cn.itstar.es.office.search.common.utils.JSONUtils;
import cn.itstar.es.office.search.common.utils.ShiroUtils;


/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class SysLogAspect {
	
	@Autowired
	private SysLogManager sysLogManager;
	
	@Pointcut("@annotation(cn.itstar.es.office.search.common.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	/**
	 * 操作异常日志
	 * @param joinPoint
	 * @param ex
	 */
	@AfterThrowing(pointcut = "logPointCut()", throwing = "ex")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLogEntity sysLog = new SysLogEntity();
		sysLog.setType(SystemConstant.LogType.ERROR.getValue());

		SysLog sysLogAnnotation = method.getAnnotation(SysLog.class);
		if(sysLogAnnotation != null){
			//注解上的描述
			sysLog.setOperation(sysLogAnnotation.value());
		}
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		//请求的参数
		Object[] args = joinPoint.getArgs();

		try{
			String params = "";
			for(int i = 0; i < args.length; i ++) {
				if(args[i].getClass().isArray()) {
					Object[] objs = (Object[]) args[i];
					for (Object object : objs) {
						if(!(object instanceof MultipartFile)){
							params += JSONUtils.beanToJson(object);
						}
					}
				}else {
					params += JSONUtils.beanToJson(args[i]);
				}
			}
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//设置IP地址
		sysLog.setIp(IpUtils.getIpAddr());

		//用户名
		sysLog.setUserId(ShiroUtils.getUserId());
		sysLog.setUsername(ShiroUtils.getUserEntity().getUsername());

		//操作状态和结果
		sysLog.setResult(SystemConstant.StatusType.DISABLE.getValue());
		sysLog.setRemark("异常信息：" + ex.getMessage());

		sysLogManager.saveLog(sysLog);
	}

	/**
	 * 操作日志
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("logPointCut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = joinPoint.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLogEntity sysLog = new SysLogEntity();

		SysLog sysLogAnnotation = method.getAnnotation(SysLog.class);
		if(sysLogAnnotation != null){
			//注解上的描述
			sysLog.setOperation(sysLogAnnotation.value());
			String type = sysLogAnnotation.type();
			if (StringUtils.isNotEmpty(type)) {
				sysLog.setType(SystemConstant.LogType.valueOf(type).getValue());
			} else {
				sysLog.setType(SystemConstant.LogType.OPERATION.getValue());
			}
		}
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		//请求的参数
		Object[] args = joinPoint.getArgs();
		
		try{
			String params = "";
			for(int i = 0; i < args.length; i ++) {
				if(args[i].getClass().isArray()) {
					Object[] objs = (Object[]) args[i];
					for (Object object : objs) {
						if(!(object instanceof MultipartFile)){
							params += JSONUtils.beanToJson(object);
						}
					}
				}else {
					params += JSONUtils.beanToJson(args[i]);
				}
			}
			//String params = JSONUtils.beanToJson(args[0]);
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//设置IP地址
		sysLog.setIp(IpUtils.getIpAddr());

		//用户名
		sysLog.setUserId(ShiroUtils.getUserId());
		sysLog.setUsername(ShiroUtils.getUserEntity().getUsername());

		//操作执行状态
		if (result instanceof R) {
			R r = (R) result;
			int code = (int) r.get("code");
			if(code == 0) {
				//操作成功
				sysLog.setResult(SystemConstant.StatusType.ENABLE.getValue());

				//响应时间：ms
				sysLog.setRemark("响应时间：" + time + "ms");
			} else {
				sysLog.setResult(SystemConstant.StatusType.DISABLE.getValue());
				sysLog.setRemark(String.valueOf(r.get("msg")));
			}
		}

		//保存系统日志
		sysLogManager.saveLog(sysLog);
		return result;
	}

}
