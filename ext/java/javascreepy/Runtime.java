package javascreepy;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.jruby.Ruby;
import org.jruby.RubyBignum;
import org.jruby.RubyBoolean;
import org.jruby.RubyClass;
import org.jruby.RubyFixnum;
import org.jruby.RubyFloat;
import org.jruby.RubyObject;
import org.jruby.RubyString;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

import static org.jruby.RubyNumeric.int2fix;
import static org.jruby.RubyNumeric.num2int;
import static org.jruby.RubyNumeric.num2dbl;
import static org.jruby.javasupport.util.RuntimeHelpers.invoke;

class Runtime extends RubyObject {

        protected String lang;
        protected ScriptEngine engine;

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
        
        @JRubyMethod
        public IRubyObject eval(ThreadContext context, IRubyObject scriptObject) {
                Ruby ruby = context.getRuntime();
                String script = scriptObject.convertToString().asJavaString();
                
                try{
                        this.engine.eval(script);
                } catch(ScriptException ex) {
                        throw ruby.newRuntimeError("Ups, an error occurred inside the engine:\n"+ex.getMessage());
                }
                
                return ruby.getNil();
        }

        @JRubyMethod(name="[]")
        public IRubyObject get(ThreadContext context, IRubyObject keyObject) {
                Ruby ruby = context.getRuntime();
                Object val = this.engine.get(keyObject.convertToString().asJavaString());

                if(val == null) {
                        return ruby.getNil();
                } else if(val instanceof String) {
                        return ruby.newString((String) val);
                } else if(val instanceof Double || val instanceof Float) {
                        return ruby.newFloat(((Number) val).doubleValue());
                } else if(val instanceof Integer || val instanceof Long) {
                        return ruby.newFixnum(((Number) val).longValue());
                } else if(val instanceof Boolean) {
                        return ruby.newBoolean(((Boolean) val).booleanValue());
                }
                
                throw ruby.newRuntimeError("Whoa, I don't know how to convert that");
        }

        @JRubyMethod(name="[]=")
        public IRubyObject set(ThreadContext context, IRubyObject keyObject, IRubyObject value) {
                String key = keyObject.convertToString().asJavaString();
                if(value instanceof RubyString) {
                        this.engine.put(key, value.convertToString().asJavaString());
                } else if(value instanceof RubyFixnum) {
                        this.engine.put(key, num2int(value));
                } else if(value instanceof RubyFloat || value instanceof RubyBignum) {
                        this.engine.put(key, num2dbl(value));
                } else if(value instanceof RubyBoolean) {
                        this.engine.put(key, value.isTrue());
                } else {
                        this.engine.put(key, null);
                }
                return context.getRuntime().getNil();
        }

        @JRubyMethod(name = "started?")
        public IRubyObject started_p(ThreadContext context) {
                Ruby ruby = context.getRuntime();
                return ruby.newBoolean(this.engine != null);
        }

        @JRubyMethod
        public IRubyObject start(ThreadContext context) {
                this.engine = (new ScriptEngineManager()).getEngineByName(this.lang);
                this.engine.getContext().setWriter(new CreepyWriter(context.getRuntime(), this));
                return this;
        }

        @JRubyMethod
        public IRubyObject stop(ThreadContext context) {
                this.engine = null;
                return this;
        }
}
