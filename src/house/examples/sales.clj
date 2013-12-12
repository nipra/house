(ns house.examples.sales
  (:require [cascalog.api :refer :all]
            [cascalog.logic.ops :as ops]
            [cascalog.more-taps :refer [hfs-delimited]]))

;;; http://annapawlicka.com/how-to-process-small-dataset-with-cascalog/
(defn numbers-as-strings? [& strings]
  (every? #(re-find #"^-?\d+(?:\.\d+)?$" %) strings))

(defn parse-double [txt]
  (Double/parseDouble txt))

(defn total-sales-per-city [input]
  (<- [?city ?total]
      (input :> ?store-id ?store-name ?city ?sales-string)
 
      (numbers-as-strings? ?sales-string)
      (parse-double ?sales-string :> ?sales)
 
      (ops/sum ?sales :> ?total)))

;; Doesn't work
;;; Worked!
;;; :jvm-opts ["-Xms768m" "-Xmx768m"] ; project.clj
(comment
  (let [data-in "/Users/nprabhak/Projects/Clojure/house/src/house/examples/in"
        data-out "/Users/nprabhak/Projects/Clojure/house/src/house/examples/out"]
    (?- (hfs-delimited data-out :sinkmode :replace :delimiter ",")
        (total-sales-per-city
         (hfs-delimited data-in :delimiter ","))))

  (let [data-in "hdfs://localhost:9000/sales.csv"
        data-out "hdfs://localhost:9000/sales-out"]
    (?- (hfs-delimited data-out :sinkmode :replace :delimiter ",")
        (total-sales-per-city
         (hfs-delimited data-in :delimiter ",")))))
