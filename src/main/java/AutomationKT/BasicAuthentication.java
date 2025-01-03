package AutomationKT;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonObject;

    public class BasicAuthentication{

     public static JsonObject getDefaultTestCapability() {
        JsonObject capabilities1 = new JsonObject();
        JsonObject ltOptions1 = new JsonObject();

        String user = "srikanth.vankegmail";
        String accessKey = "qUJdi3LIh0g7vM5uRqeLkkD8FzV3i5Ob46UF7Qf1q1T4fvf5Vv";

        capabilities1.addProperty("browserName", "Chrome"); // Browsers allowed: `Chrome`, `MicrosoftEdge`, `pw-chromium`, `pw-firefox` and `pw-webkit`
        capabilities1.addProperty("browserVersion", "latest");
        ltOptions1.addProperty("platform", "Windows 10");
        ltOptions1.addProperty("name", "Playwright Test");
        ltOptions1.addProperty("build", "Playwright Parallel demo");
        ltOptions1.addProperty("user", user);
        ltOptions1.addProperty("accessKey", accessKey);
        ltOptions1.addProperty("video", true); // Set to true to enable video recording
        ltOptions1.addProperty("console", true); // Enables console logs for debugging
        ltOptions1.addProperty("network", true); // Enables network logs, useful for debugging
        ltOptions1.addProperty("visual", true); // Captures visual logs (optional)
        // Optionally, set screen resolution to standardize video quality
        //ltOptions1.addProperty("screenResolution", "1280x1024");
        capabilities1.add("LT:Options", ltOptions1);

        return capabilities1;
    }
    }
