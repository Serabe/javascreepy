require File.expand_path(File.dirname(__FILE__) + '/spec_helper')

describe "Javascreepy" do

  describe "#new" do
    it "should not raise an exception" do
      lambda{ Runtime.new }.should_not raise_error
    end

    it "should take javascript as default language" do
      Runtime.new.lang.should == "javascript"
    end
  end
end
