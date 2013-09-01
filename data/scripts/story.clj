(ns story)

(def story-stage :contact)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;; Mechanic ;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def mechanic-know false)

(bind "mechanic"
      (fn []
          (cond
              (not mechanic-know) (run "mechanic-contact")
              :else (run "mechanic-empty"))))

;; First contact with mechanic
(bind "mechanic-contact"
      (fn []
          (run (t "[Mechanic description]"
                  (list
                      (ln "..."
                          (fn []
                              (redef mechanic-know true)
                              false)))))))

;; If you approach mechanic when there is no story
;; could contain some generic dialogue for fun
(bind "mechanic-empty"
      (t "I approached mechanic.."
         (list
             (ln "..." nil))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;; Scientist ;;;;;;;;;;;;;;;;;;;;;;;;;;
(def scientist-know false)

(bind "scientist"
      (fn []
          (cond
              (not scientist-know) (run "scientist-contact")
              :else (run "scientist-empty"))))

(bind "scientist-contact"
      (fn []
          (run (t "[Scientist description]"
                  (list
                      (ln "..." nil))))
          (redef scientist-know true)
          true))

(bind "scientist-empty"
      (t "I approached scientist.."
         (list
             (ln "..." nil))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;; Writer ;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def writer-know false)

(bind "writer"
      (fn []
          (cond
              (not writer-know) (run "writer-contact")
              :else (run "writer-empty"))))

(bind "writer-contact"
      (fn []
          (run (t "[writer description]"
                  (list
                      (ln "..." nil))))
          (redef writer-know true)
          true))

(bind "writer-empty"
      (t "I approached writer.."
         (list
             (ln "..." nil))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;; Policeman ;;;;;;;;;;;;;;;;;;;;;;;;;;
(def policeman-know false)

(bind "policeman"
      (fn []
          (cond
              (not policeman-know) (run "policeman-contact")
              :else (run "policeman-empty"))))

(bind "policeman-contact"
      (fn []
          (run (t "[Policeman description]"
                  (list
                      (ln "..." nil))))
          (redef policeman-know true)
          true))

(bind "policeman-empty"
      (t "I approached policeman.."
         (list
             (ln "..." nil))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(bind "_init" (ev "welcome"))

(bind "_dialogexit"
      (fn []
          (cond
              (and mechanic-know scientist-know writer-know policeman-know)
                (run (t "rain!")))))

(bind "_update"
      (fn []))
