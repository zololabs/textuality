(ns zolo.textuality.parser-test
  (:require [clojure.test :refer :all]
            [zolo.textuality.parser :refer :all]))

(deftest parser
  (testing "converts simple html dom to readable text"
    (is (= "Let's parse some html"
           (parse "<html><div>Let's parse some html</div></html>"))))

  (testing "converts a link to readable text"
    (is (= "text \n[1] See more reports like this\n\thttp://u754798.sendgrid.org/wf/click?upn=uAyRCmYSPjbz43RAy9Uu-2Bi-2B8"
           (parse "<html><div>text <a href=\"http://u754798.sendgrid.org/wf/click?upn=uAyRCmYSPjbz43RAy9Uu-2Bi-2B8\">See more reports like this</a></div></html>"))))
  
  (testing "converts simple html dom with a link to readable text"
    (is (= "text \n[1] \n\thttp://www.google.com"
           (parse "<html><div>text <a href=\"http://www.google.com\"></a></div></html>"))))

  (testing "converts slightly comples html dom to readable test"
    (is (= "This is \ncrazy"
           (parse "<html><div id=\"scissors\"><p>This is <em class=\"paper\">crazy</em></p></div></html>"))))
  
  (testing "weeds out nbsp tags from the html before parsing"
    (is (= "Let's add some space"
           (parse "<p>Let's add some&nbsp; space</p>"))))
  
  (testing "weeds out copy tags from the html before parsing"
    (is (= "Let's add some copy tags"
           (parse "<p>Let's add some copy&copy; tags</p>"))))

  (testing "cleanses input text before parsing"
    (is (= "A yellow submarine!"
           (parse "<div style=\"background-color:#FFFFFF\">A yellow submarine!</div>"))))

  (testing "converts nested html dom to readable test"
    (is (= "Let's parse some \nnested\n html"
           (parse "<html><div>Let's parse some <p>nested</p> html</div></html>")))))