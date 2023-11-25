package ro.Gabriel.Managers;

import ro.Gabriel.Class.Validators.ManagerClassValidator;
import ro.Gabriel.Managers.Annotations.ManagerClass;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Class.ClassManager;
import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Misc.Utils;

import java.util.stream.Stream;

public class ManagersProcessor {
    public static void load() {
        ClassManager.searchClasses(ManagerClassValidator.class).forEach(clazz -> {
                try {
                    if(ReflectionUtils.getValue(null, true, clazz, "INSTANCE") == null) {
                        ManagerClass managerClass = Utils.getAnnotation(clazz, ManagerClass.class);

                        if(managerClass.managersBefore().length > 0) {
                            Stream.of(managerClass.managersBefore()).filter(mClass -> {
                                try {
                                    return ReflectionUtils.getValue(null, true, mClass, "INSTANCE") == null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return false;
                            }).forEach(mClass -> {
                                try {
                                    Main.sendMessage("&4S-a gasit clasa de manager: &7" + mClass.getName());
                                    mClass.getMethod("load").invoke(mClass);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        Main.sendMessage("&4S-a gasit clasa de manager: &7" + clazz.getName());
                        clazz.getMethod("load").invoke(clazz);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
    }
}