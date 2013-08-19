(ns zolo.textuality.parser
  (:require [clojure.zip :as zip]
            [clojure.xml :as xml]
            [clojure.string :as string]))
(import 'org.apache.commons.codec.net.QuotedPrintableCodec)

(defn zclean [s] (-> s (.replace "&nbsp;" "") (.replace "&copy;" "")))
(def not-nil? (comp not nil?))

(def section-counter (atom 0))
(def link-counter (atom 0))

(defn increment-link-counters []
  (swap! section-counter inc)
  (reset! link-counter 0))

(defn reset-link-counters []
  (reset! section-counter 0)
  (reset! link-counter 0))

(defn link-placeholder []
  (string/join ["__section" (str @section-counter) "-link" (str @link-counter) "__\n"]))

(defn append-link-name [node link-names]
  (if (and (map? node) (= :a (node :tag)) (node :content))
    (assoc link-names (keyword (link-placeholder)) (first (node :content)))
    link-names
    ))

(defn append-link-url [node link-urls]
  (if (and (map? node) (= :a (node :tag)) (node :attrs))
    (assoc link-urls (keyword (link-placeholder)) ((node :attrs) :href))
    link-urls
  ))

(defn parse-node [node result]
  (if (map? node)
    (cond
     (and (= :a (node :tag)) (node :attrs))
       (do
         (swap! link-counter inc)
         (conj result (link-placeholder)))
     (and (node :content) (string? (first (node :content))))
       (do
         (swap! section-counter inc)
         (conj result (str (first (node :content)) "\n")))
     )
    result
    ))

(defn format-link [line link-names link-urls]
   (str "[" (last (re-seq #"\d+" line)) "] " (link-names (keyword line)) "\n\t" (link-urls (keyword line)))
  )

(defn replace-links [result link-names link-urls]
  (for [line result]
    (string/replace line #"^__.*__$" (format-link line link-names link-urls))
    ))

(defn decode-quoted-printable [content]
  (.decode (new QuotedPrintableCodec) content)) 

(defn parse-tree [dom]
  (reset-link-counters)
   (loop [loc (zip/xml-zip dom) result [] link-names {} link-urls {}]
    (if (zip/end? loc)
      (string/trim (apply str (replace-links (reverse result) link-names link-urls)))
      (let [node (zip/node loc)]
        (recur (zip/next loc)
               (parse-node node result)
               (append-link-name node link-names)
               (append-link-url node link-urls))
  ))))

(defn parse [html]
  (-> html zclean decode-quoted-printable .getBytes java.io.ByteArrayInputStream. xml/parse parse-tree))