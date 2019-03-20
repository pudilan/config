package com.sintmo.scs.activiti;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ActivitiTest {

    @Autowired
    private ProcessEngine processEngine;

    /**
     * 部署工作流
     */
    @Test
    public void deploy() {
        // 获取仓库服务 ：管理流程定义
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()// 创建一个部署的构建器
                .addClasspathResource("holiday.bpmn")// 从类路径中添加资源,一次只能添加一个资源
                .name("请求单流程")// 设置部署的名称
                .category("办公类别")// 设置部署的类别
                .deploy();

        System.out.println("部署的id" + deploy.getId());
        System.out.println("部署的名称" + deploy.getName());
    }

    /**
     * 执行工作流
     */
    @Test
    public void startProcess() {
        String processDefiKey = "demo2";// bpmn 的 process id属性
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey(processDefiKey);

        System.out.println("流程执行对象的id：" + pi.getId());// Execution 对象
        System.out.println("流程实例的id：" + pi.getProcessInstanceId());// ProcessInstance 对象
        System.out.println("流程定义的id：" + pi.getProcessDefinitionId());// 默认执行的是最新版本的流程定义
    }

    /**
     * 根据代理人查询当前任务的信息
     */
    // 查询任务
    @Test
    public void queryTask() {
        // 任务的办理人
        String assignee = "wang";
        // 取得任务服务
        TaskService taskService = processEngine.getTaskService();
        // 创建一个任务查询对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        // 办理人的任务列表
        List<Task> list = taskQuery.taskAssignee(assignee).list();
        // 遍历任务列表
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务的办理人：" + task.getAssignee());
                System.out.println("任务的id：" + task.getId());
                System.out.println("任务的名称：" + task.getName());
            }
        }
    }

    /**
     * 完成任务
     */
    @Test
    public void compileTask() {
        String taskId = "7504";
        // taskId：任务id
        processEngine.getTaskService().complete(taskId);
        System.out.println("当前任务执行完毕");
    }

    /**
     * 查看流程定义
     */
    @Test
    public void queryProcessDefination() {
        String processDefiKey = "demo2";// 流程定义key
        // 获取流程定义列表
        List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery()
                // 查询 ，好比where
                // .processDefinitionId(proDefiId) //流程定义id
                // 流程定义id ： buyBill:2:704 组成 ： proDefikey（流程定义key）+version(版本)+自动生成id
                .processDefinitionKey(processDefiKey)// 流程定义key 由bpmn 的 process 的 id属性决定
                // .processDefinitionName(name)//流程定义名称 由bpmn 的 process 的 name属性决定
                // .processDefinitionVersion(version)//流程定义的版本
                .latestVersion()// 最新版本

                // 排序
                .orderByProcessDefinitionVersion().desc()// 按版本的降序排序

                // 结果
                // .count()//统计结果
                // .listPage(arg0, arg1)//分页查询
                .list();

        // 遍历结果
        if (list != null && list.size() > 0) {
            for (ProcessDefinition temp : list) {
                System.out.print("流程定义的id: " + temp.getId());
                System.out.print("流程定义的key: " + temp.getKey());
                System.out.print("流程定义的版本: " + temp.getVersion());
                System.out.print("流程定义部署的id: " + temp.getDeploymentId());
                System.out.println("流程定义的名称: " + temp.getName());
            }
        }
    }

    /**
     * 删除流程定义
     */
    @Test
    public void deleteProcessDefi() {
        // 通过部署id来删除流程定义
        String deploymentId = "101";
        processEngine.getRepositoryService().deleteDeployment(deploymentId);
    }

    // 获取流程实例的状态
    @Test
    public void getProcessInstanceState() {
        String processInstanceId = "7501";
        ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();// 返回的数据要么是单行，要么是空 ，其他情况报错
        // 判断流程实例的状态
        if (pi != null) {
            System.out.println("该流程实例" + processInstanceId + "正在运行...  " + "当前活动的任务:" + pi.getActivityId());
        } else {
            System.out.println("当前的流程实例" + processInstanceId + " 已经结束！");
        }
    }

    // 查看历史执行流程实例信息
    @Test
    public void queryHistoryProcInst() {
        List<HistoricProcessInstance> list = processEngine.getHistoryService().createHistoricProcessInstanceQuery()
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricProcessInstance temp : list) {
                System.out.println("历史流程实例id:" + temp.getId());
                System.out.println("历史流程定义的id:" + temp.getProcessDefinitionId());
                System.out.println("历史流程实例开始时间--结束时间:" + temp.getStartTime() + "-->" + temp.getEndTime());
            }
        }
    }

    // 执行流程，开始跑流程
    @Test
    public void startProcessWithAssign() {
        String processDefiKey = "demo2";// bpmn 的 process id属性
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userID", "wang");
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey(processDefiKey, params);

        System.out.println("流程执行对象的id：" + pi.getId());// Execution 对象
        System.out.println("流程实例的id：" + pi.getProcessInstanceId());// ProcessInstance
        // 对象
        System.out.println("流程定义的id：" + pi.getProcessDefinitionId());// 默认执行的是最新版本的流程定义
    }

    @Test
    public void showImages() throws Exception {
        String processDefiKey = "demo2";// bpmn 的 process id属性
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userID", "wang");
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefiKey,
                params);

        // 得到流程定义实体类
        // ProcessDefinitionEntity pde = (ProcessDefinitionEntity)
        // ((RepositoryServiceImpl) repositoryService)
        // .getDeployedProcessDefinition(processInstance
        // .getProcessDefinitionId());
        BpmnModel bpmnModel = processEngine.getRepositoryService()
                .getBpmnModel(processInstance.getProcessDefinitionId());

        // 得到流程执行对象
        List<Execution> executions = processEngine.getRuntimeService().createExecutionQuery()
                .processInstanceId(processInstance.getId()).list();
        // 得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<String>();
        for (Execution exe : executions) {
            List<String> ids = processEngine.getRuntimeService().getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
        InputStream in = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
                .generateDiagram(bpmnModel, "png", activityIds);
        FileOutputStream out = new FileOutputStream("d:\\watch.png");
        FileCopyUtils.copy(in, out);
    }

}
