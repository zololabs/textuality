(ns zolo.textuality.core-test
  (:require [clojure.test :refer :all]
            [zolo.textuality.core :refer :all]))

(deftest html-parsing
  (testing "parses text out of a dom document"
    (is  (= "sentences" (text-from "<p>sentence</p>"))))

  (testing "parses text from a string including a DOM document"
    (is (= "Another sentence" (text-from "Another <p>sentence</p>"))))

  (testing "parses text out of a malformed DOM document"
    (is (= "And another one" (text-from "And <div>another<p>one</p>"))))

  (testing "parses out links"
    (is (= "[1] Google\nLinks:\n1. http://www.google.com" (text-from "<a href=\"http://www.google.com\">Google</a>"))))
  )

(deftest visitor
  (testing "converts html dom to reading text"
    (is (= "See links\n\nLinks\nGoogle\nhttp://www.google.com"
           (convert-html "<html><p>See links</p><a href=\"http://www.google.com\">Google</a></html>")))))