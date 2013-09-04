(ns story)

(def story-stage :day0-0-contact)
(def restrict-moving false)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;; Mechanic ;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def mechanic-know false)

(bindf "mechanic"
      (cond
          (not mechanic-know) (run "mechanic-contact")
          :else (run "mechanic-empty")))

;; First contact with mechanic
(bindf "mechanic-contact"
       (run (t "[Mechanic description]"
               (list
                   (lnf "..."
                        (redef mechanic-know true)
                        false)))))

;; If you approach mechanic when there is no story
;; could contain some generic dialogue for fun
(bind "mechanic-empty"
      (t "I approached mechanic.."
         (list
             (ln "..." nil))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;; Scientist ;;;;;;;;;;;;;;;;;;;;;;;;;;
(def scientist-know false)

(bindf "scientist"
       (cond
           (not scientist-know) (run "scientist-contact")
           :else (run "scientist-empty")))

(bindf "scientist-contact"
       (run (t "[Scientist description]"
               (list
                   (ln "..." nil))))
       (redef scientist-know true))

(bind "scientist-empty"
      (t "I approached scientist.."
         (list
             (ln "..." nil))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;; Writer ;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def writer-know false)

(bindf "writer"
       (cond
           (not writer-know) (run "writer-contact")
           :else (run "writer-empty")))

(bindf "writer-contact"
       (run (t "[writer description]"
               (list
                   (ln "..." nil))))
       (redef writer-know true))

(bind "writer-empty"
      (t "I approached writer.."
         (list
             (ln "..." nil))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;; Policeman ;;;;;;;;;;;;;;;;;;;;;;;;;;
(def policeman-know false)

(bindf "policeman"
       (cond
           (not policeman-know) (run "policeman-contact")
           :else (run "policeman-empty")))

(bindf "policeman-contact"
       (run (t "[Policeman description]"
               (list
                   (ln "..." nil))))
       (redef policeman-know true))

(bind "policeman-empty"
      (t "I approached policeman.."
         (list
             (ln "..." nil))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;; Places ;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(bindf "control"
       (cond
           (= story-stage :day0-0-contact) (run (t "I want to meet people first!.."))
           (= story-stage :day0-1-investigate-ship) (do (run (t "I've entered the room")) (run "go-control"))
           :else (run (t "not implemented"))))

(bindf "investigation-target"
       (cond
           (= story-stage :day0-0-contact) (log "This should never happen :/")
           (= story-stage :day0-1-investigate-ship) (run "day0-2-found-control")
           :else (run (t "not implemented"))))

(bindf "down"
       (cond
           (= story-stage :day0-0-contact) (run (t "I want to meet people first!.."))
           (= story-stage :day0-1-investigate-ship) (run (t "I want to investigate this level first"))
           (= story-stage :day0-2-found-control) (run "day0-3-go-down")
           (not restrict-moving) (run "go-down")
           :else (run (t "can't go down for unknown reason.."))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(bind "welcome"
      (t "Wilkamen!"))

(bind "day0-1-investigate-ship"
      (t "So we decided to look at ship.."))


(bindf "day0-2-found-control"
       (def story-stage :day0-2-found-control)
       (run (t "Found control..")))

(bindf "go-down"
       (change-map "cabins"))

(bindf "go-control"
       (self-move -1 0))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; called on init
(bind "_init" (ev "welcome"))

;; called after redef changed something..
(bindf "_update"
       (cond
           (= story-stage :day0-1-investigate-ship)
             nil
           (and mechanic-know scientist-know writer-know policeman-know)
             (do
                 (runafter "day0-1-investigate-ship" )
                 (def story-stage :day0-1-investigate-ship))))
