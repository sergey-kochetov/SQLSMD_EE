package com.juja.controller.web;

import com.juja.service.Service;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class ActionResolver {
    @Autowired
    private Service service;
    private List<Action> actions;

    public ActionResolver() {
        actions = new LinkedList<>();
        actions.addAll(Arrays.asList(
                new MenuAction(service, actions),
                //new ClearAction(service),
                new ConnectAction(service),
                //new DropAction(service),
                //new EditeAction(service),
                //new FindAction(service),
                new HelpAction(service),
                //new InsertAction(service),
                //new SuccessAction(service),
                new TablesAction(service),
                //new UpdateAction(service),
                new ErrorAction(service)
        ));

    }

    private List<Action> createConstructorWithReflection() {
        List<Action> result  = new LinkedList<>();

        Reflections reflections = new Reflections(this.getClass().getPackage().getName());
        Set<Class<? extends AbstractAction>> classes =
                reflections.getSubTypesOf(AbstractAction.class);
        for (Class<? extends AbstractAction> aClass : classes) {
            if (aClass.equals(ErrorAction.class)) {
                continue;
            }
            try {
                AbstractAction abstractAction = aClass.getConstructor(Service.class).newInstance(service);
                result.add(abstractAction);
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
        result.add(new ErrorAction(service));
        return result;
    }

    public Action getAction(String url) {
        for (Action action : actions) {
            if (action.canProcess(url)) {
                return action;
            }
        }
        return new NullAction();
    }
}
