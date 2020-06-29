import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

import javax.script.ScriptEngine;

public class GraalSetup {
    private static HostAccess getHostAccess() {
        HostAccess.Builder builder = HostAccess.newBuilder(HostAccess.ALL);
        return builder.build();
    }

    public static ScriptEngine engine() throws Exception {
        return GraalJSScriptEngine.create(null, Context.newBuilder("js").allowAllAccess(true).allowHostAccess(getHostAccess()));
    }
}
