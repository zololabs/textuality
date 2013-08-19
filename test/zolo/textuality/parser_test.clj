(ns zolo.textuality.parser-test
  (:require [clojure.test :refer :all]
            [zolo.textuality.parser :refer :all]))

(deftest parser
  (testing "converts simple html dom to readable text"
    (is (= "Let's parse some html"
           (parse "<html><div>Let's parse some html</div></html>"))))

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
           (parse "<div style=3D\"background-color:#FFFFFF\">A yellow submarine!</div>"))))

  (comment - Condition not handled yet
    (testing "converts nested html dom to readable test"
     (is (= "Let's parse some \nnested\n html"
            (parse "<html><div>Let's parse some <p>nested</p> html</div></html>"))))
    ))