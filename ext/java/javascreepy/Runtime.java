package javascreepy;

import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.RubyObject;
import org.jruby.RubyString;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

import static org.jruby.javasupport.util.RuntimeHelpers.invoke;

class Runtime extends RubyObject {

        protected String lang;

        public Runtime(Ruby runtime, RubyClass klazz) {
                super(runtime, klazz);
        }
        
        public String getLang() {
                return this.lang;
        }
        
        public void setLang(String lang) {
                this.lang = lang;
        }

        @JRubyMethod(meta=true, name="new", required=0, optional=1)
        public static IRubyObject rbNew(ThreadContext context, IRubyObject klazz, IRubyObject[] params) {
                Ruby ruby = context.getRuntime();
                RubyString lang;
                if(params.length == 0){
                        lang = ruby.newString("javascript");
                } else {
                        lang = params[0].convertToString();
                }
                
                Runtime rt = new Runtime(ruby, (RubyClass) klazz);
                
                invoke(context, rt, "initialize", lang);
                
                rt.setLang(lang.asJavaString());
                
                return rt;
        }
}
