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

    it "should fail if there's no engine for language" do
      lang = "this_is_not_a_valid_language_or_at_least_i_hope_so"
      lambda{ Javascreepy::Runtime.new(lang).start}.should raise_error(Javascreepy::EngineNotFoundError)
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
      @rt = Javascreepy::Runtime.new.start
    end
    
    it "should add a variable" do
      [5, true, false, "hej", nil, 5.6].each do |value|
        @rt['a'] = value
        @rt['a'].should == value
      end
    end
    
    it "should modify existing variables" do
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
