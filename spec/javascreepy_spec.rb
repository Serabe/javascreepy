require File.expand_path(File.dirname(__FILE__) + '/spec_helper')

describe Javascreepy::Runtime do
  describe "#new" do
    it "should not raise an exception" do
      lambda{ Javascreepy::Runtime.new }.should_not raise_error
    end

    it "should take javascript as default language" do
      Javascreepy::Runtime.new.lang.should == "javascript"
    end
  end
  
  describe "#started?" do

    it "should be true iff started" do
      Javascreepy::Runtime.new.should_not be_started
      Javascreepy::Runtime.new.start.should be_started
      Javascreepy::Runtime.new.start.stop.should_not be_started
    end
  end

  describe "#[]=" do
    
    before(:each) do
      @rt = Javascreepy::Runtime.new
      @rt.start
      @rt.events = SpecEvents.new
    end
    
    it "should add a variable" do
      @rt['a'] = 5
      @rt.eval('alert(a);')
      # TODO: redo using Runtime#[]
      @rt.events.value.should == "5"
    end
    
    it "should modify existing variables" do
      @rt.eval('a=6;')
      @rt['a'] = 5
      @rt.eval('alert(a);')
      # TODO: redo using Runtime#[]
      @rt.events.value.should == "5"
    end

    it "should fail if runtime not started"

    after(:each) do
      @rt.stop
    end
  end
end
