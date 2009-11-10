package javascreepy;

import java.io.Writer;
import org.jruby.Ruby;
import org.jruby.RubyString;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

import static org.jruby.javasupport.util.RuntimeHelpers.invoke;
import static org.jruby.javasupport.util.RuntimeHelpers.getInstanceVariable;

class CreepyWriter extends Writer {
        
        Ruby ruby;
        Runtime runtime;
        
        public CreepyWriter(Ruby ruby, Runtime runtime) {
                this.ruby = ruby;
                this.runtime = runtime;
        }
        
        public void write(char[] buf, int off, int len){
                String out = new String(buf, off, len);
                ThreadContext context = ruby.getCurrentContext();
                IRubyObject events = getInstanceVariable(this.runtime, ruby, "@events");
                if(events.isNil()) {
                        System.out.println(out);
                } else {
                        invoke(context, events, "write", this.ruby.newString(out));
                }
        }
        
        public void flush() {}
        
        public void close() {}
}