package com.sintmo.scs.activiti.lsnr;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class AuditCreateListener implements TaskListener {

    private static final long serialVersionUID = -992548977604245271L;

    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        System.out.println("#########################AuditCreateListener:" + eventName);
    }

}
