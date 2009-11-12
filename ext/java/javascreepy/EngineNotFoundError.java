package javascreepy;

import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.RubyException;
import org.jruby.RubyString;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;


public class EngineNotFoundError extends RubyException {
	protected String lang;
        protected RubyString message;

	public EngineNotFoundError(Ruby ruby) {
		this(ruby, (RubyClass) ruby.getClassFromPath("Javascreepy::EngineNotFoundError"));
	}


	public EngineNotFoundError(Ruby ruby, RubyClass klazz) {
		super(ruby, klazz);
                this.lang = "";
	}

	public EngineNotFoundError(Ruby ruby, String lang) {
		this(ruby);
		this.lang = lang;
	}

	public EngineNotFoundError(Ruby ruby, RubyClass klazz, String lang) {
		this(ruby, klazz);
		this.lang = lang;
	}

        @JRubyMethod
        @Override
        public IRubyObject message(ThreadContext context) {
                if(this.message == null) {
                        this.message = context.getRuntime().newString("Engine for language " + this.lang + " not found.");
                }
                return this.message;
        }
}
