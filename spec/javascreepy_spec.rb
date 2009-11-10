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

  describe "#start" do

    it "should fail if there's no engine for language"
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
      @rt = Javascreepy::Runtime.new.start
     # @rt.events = SpecEvents.new
    end
    
    it "should add a variable" do
      [5, true, false, "hej", nil, 5.6].each do |value|
	@rt['a'] = value
	@rt['a'].should == value
      end
    end
    
    it "should modify existing variables" do
      pending "implement eval, lazy ass!"
      [5, true, false, "hej", nil, 5.6].each do |value|
	@rt.eval('a=6;')
	@rt['a'] = value
	@rt['a'].should == value
      end
    end

    it "should fail if runtime not started"

    after(:each) do
      @rt.stop
    end
  end
end
