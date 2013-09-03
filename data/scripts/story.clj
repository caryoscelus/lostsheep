(ns story)

(def story-stage :contact)

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
           (= story-stage :contact) (run (t "I want to meet people first!.."))
           (= story-stage :investigate-ship) (run (t "Ok.."))
           :else (run (t "not implemented"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(bind "after-meeting"
      (t "So we decided to look at ship.."))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(bind "_init" (ev "welcome"))

(bindf "_dialogexit"
       (cond
           (and mechanic-know scientist-know writer-know policeman-know)
             (do
                 (run "after-meeting")
                 (redef story-stage :investigate-ship))))

(bindf "_update")
