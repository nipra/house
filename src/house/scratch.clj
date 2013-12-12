(ns house.scratch
  (:use [cascalog.api])
  (:use [cascalog.playground])
  (:use [cascalog.more-taps :only (hfs-delimited)])
  (:gen-class))

(defn hello-world
  []
  (println "Hello world!"))

(comment
  (defn -main [in out & args]
    (?<- (hfs-delimited out)
         [?doc ?line]
         ((hfs-delimited in :skip-header? true) ?doc ?line)))
  
  (?<- (stdout)
       [?doc ?line]
       ((hfs-delimited "/Users/nprabhak/Softwares/Hadoop/Cascalog/Impatient-Cascalog/part1/data/rain.txt" :skip-header? true) ?doc ?line))

  (?<- (hfs-delimited "/tmp/out")
       [?doc ?line]
       ((hfs-delimited "/Users/nprabhak/Softwares/Hadoop/Cascalog/Impatient-Cascalog/part1/data/rain.txt" :skip-header? true) ?doc ?line))

  "(nprabhak@nprabhak-mn ~/Projects/Clojure/house)$ hadoop jar target/house-0.1.0-SNAPSHOT-standalone.jar house.scratch /data/rain.txt /cascalog-out"

  (?<- (hfs-delimited "hdfs://localhost:9000/cascalog-out")
       [?doc ?line]
       ((hfs-delimited "hdfs://localhost:9000/data/rain.txt" :skip-header? true) ?doc ?line)))

(comment
  (?- (stdout)
      sentence))
