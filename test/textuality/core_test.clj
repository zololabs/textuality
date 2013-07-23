(ns textuality.core-test
  (:require [clojure.test :refer :all]
            [textuality.core :refer :all]))

(deftest html-parsing
  (testing "parses text out of a dom document"
    (is  (= "sentence" (text-from "<p>sentence</p>"))))

  (testing "parses text from a string including a DOM document"
    (is (= "Another sentence" (text-from "Another <p>sentence</p>"))))

  (testing "parses text out of a malformed DOM document"
    (is (= "And another one" (text-from "And <div>another<p>one</p>"))))
  )
