module Javascreepy
  class Runtime

    attr_reader :lang
    attr_accessor :events
    
    def initialize(lang)
      @lang = "javascript"
      @events = Events.new
    end

	["[]=", :"[]", :eval].each do |name|
	  old = instance_method(name)
	  self.send(:define_method, name) do |*args|
	    raise EngineNotStartedError, "engine must be started before calling #{name}" unless self.started?
	    old.bind(self).call(*args)
	  end	  
	end
  end
end

class EngineNotStartedError < StandardError; end
