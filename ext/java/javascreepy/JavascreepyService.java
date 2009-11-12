package javascreepy;

import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.RubyModule;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.BasicLibraryService;

public class JavascreepyService implements BasicLibraryService {
        
        public boolean basicLoad(Ruby ruby) {
                init(ruby);
                return true;
        }

        public static void init(Ruby ruby) {
                RubyModule javascreepy = ruby.defineModule("Javascreepy");
                init_runtime(ruby, javascreepy);
                init_engine_not_found_error(ruby, javascreepy);
        }

        public static void init_runtime(Ruby ruby, RubyModule javascreepy) {
                RubyModule runtime = javascreepy.defineClassUnder("Runtime", ruby.getObject(), RUNTIME_ALLOCATOR);

                runtime.defineAnnotatedMethods(Runtime.class);
        }

        public static void init_engine_not_found_error(Ruby ruby, RubyModule javascreepy) {
                RubyModule error = javascreepy.defineClassUnder("EngineNotFoundError", ruby.getStandardError(), ruby.getStandardError().getAllocator());
                error.defineAnnotatedMethods(EngineNotFoundError.class);
        }

        public static ObjectAllocator RUNTIME_ALLOCATOR = new ObjectAllocator() {
                public IRubyObject allocate(Ruby runtime, RubyClass klazz) {
                        return new Runtime(runtime, klazz);
                }
        };
}
