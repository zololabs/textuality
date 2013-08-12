(ns zolo.textuality.parser
  (:require [clojure.zip :as zip]
            [clojure.xml :as xml]))
(import 'org.jsoup.Jsoup)

(defn zclean [s]
        (-> s 
            (.replace "&nbsp;" "")
            (.replace "&copy;" "")))

(defn print-tree [dom]
  (loop [loc (zip/xml-zip dom) result []]
    (if (zip/end? loc)
      (clojure.string/join "\n" result)
      (let [node (zip/node loc)]
        (recur (zip/next loc)
               (cond
                (string? node) (conj result node)
                (and (map? node) (= :a (node :tag)) (node :attrs)) (conj result ((node :attrs) :href))
                :else result
                ))
        ))))

(defn parse [html]
  (-> html zclean (Jsoup/clean (org.jsoup.safety.Whitelist/relaxed)) Jsoup/parse .body .text print-tree))
