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
end
