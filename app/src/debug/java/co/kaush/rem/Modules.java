package co.kaush.rem;

import java.util.ArrayList;
import java.util.List;

public class Modules {
    //public static List<Object> testModules = null;

    private Modules() {
        // Don't allow instances.
    }

    public static Object[] list(MyApp app) {

        List<Object> modules = new ArrayList<>();
        modules.add(new RemModule(app));
        modules.add(new DebugRemModule());

        // Injected from UI Tests
        //if (testModules != null) {
        //    modules.addAll(testModules);
        //}

        return modules.toArray();
    }
}
