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
  
  describe "#[]=" do
    
    before(:each) do
      @rt = Javascreepy::Runtime.new
      @rt.events = SpecEvents.new
    end
    
    it "should add a variable" do
      @rt['a'] = 5
      @rt.eval('alert(a);')
      @rt.events.value.should == "5"
    end
    
    it "should modify existing variables" do
      @rt.eval('a=6;')
      @rt['a'] = 5
      @rt.eval('alert(a);')
      @rt.events.value.should == "5"
    end
  end
end