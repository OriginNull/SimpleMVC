package cn.origin.util.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.origin.util.bean.ControllerRequestMapping;
import cn.origin.util.bean.InjectResourceUtil;
import cn.origin.util.bean.ScannerPackageUtil;

/**
 * @author origin
 *  核心只做了两件事：包的扫描处理；服务的分发处理
 */
public class DispatcherServlet extends HttpServlet {
    /**
     * 初始化实现包的扫描处理
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        //定义扫描包，在web,xml中进行配置，允许设置多个包，格式：包;包...
        String scanPackage = config.getInitParameter("scanPackage");
        //根据设置的初始化包的信息进行包中所有类的扫描
        ScannerPackageUtil.scannerHandle(super.getClass(),scanPackage);
    }

    /**
     * 进行业务的请求分发
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletObjectUtil.setRequest(request);
        ServletObjectUtil.setResponse(response);
        String path = "/pages/plugins/errors.jsp"; // 这是一个公共的错误页面
        // 得到访问路径
        String controllerPath = request.getServletPath().substring(0, request.getServletPath().lastIndexOf(".action"));
        // 通过访问路径获得映射的方法以及类对象
        ControllerRequestMapping crMapping = ScannerPackageUtil.getActionMapping().get(controllerPath);
        // 实例化Action类的对象
        try {
            // 实例化控制层类对象，同时这个对象里面有可能有需要进行注入的业务对象
            Object actionObject = crMapping.getActionClass().getDeclaredConstructor().newInstance();
            // 需要进行注入的操作处理
            InjectResourceUtil.resouceHandle(actionObject);
            Object backObject = crMapping.getActionMethod().invoke(actionObject);
            if (backObject != null) {
                if (backObject instanceof String) { // 返回的是字符串
                    path = backObject.toString();
                } else if (backObject instanceof ModelAndView) {
                    path = ((ModelAndView) backObject).getPath();
                }
            } else {
                path = null;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        ServletObjectUtil.clear(); // 清除线程中保存的内容
        if (path != null) {
            request.getRequestDispatcher(path).forward(request, response); // 跳转
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
}
