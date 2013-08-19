(ns zolo.textuality.handler-test
  (:use clojure.test
        ring.mock.request
        zolo.textuality.handler))

(deftest main-routes
  (testing "parse post route"
    (let [response (app (request :post "/parse" {"html" "<html>foo</html>"}))]
        (is (= (:status response) 200))
        (is (= (:body response) "foo"))))

  )

(deftest routes
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))))
  
  (comment (testing "parse route"
     (let [response (app (request :post "/parse" {"html" "<html>A <p>Sentence</p></html>"}))]
       (is (= (:status response) 200))
       (is (= (:body response) "A Sentence")))))

  
  (testing "error handling"
    (let [response (app (request :post "/parse" {} ))]
      (is (= (:status response) 400))))

)
