(ns zolo.textuality.test.handler
  (:use clojure.test
        ring.mock.request
        zolo.textuality.handler))

(deftest test-app

  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))))
  
  (testing "parse route"
    (let [response (app (request :get "/parse?html=A%20%3Cp%3ESentence%3C%2Fp%3E"))]
      (is (= (:status response) 200))
      (is (= (:body response) "A Sentence"))))

  (testing "parse post route"
    (let [response (app (request :post "/parse" {"html" "foo"}))]
        (is (= (:status response) 200))
        (is (= (:body response) "foo")))
      ))
