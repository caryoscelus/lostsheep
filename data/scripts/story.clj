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
(bindf "welcome"
       (run "day0-0-contact"))

(bind "day0-0-contact"
      (t "..I try to open my eyes, but can't see anything. I try to move my limbs, but that doesn't work.^^
         I'm lieing on hard surface, trying to remember what happend. I can't..^^
         
         Eventually, darkness starts to go away. I can see the sky, clear blue sky. Morning sky. And i can
         hear water noise. Like i'm on the sea shore.. I try to sit up again and what i see is..
         i can't trust my eyes, this must be a dream! How can i happen to be on some weird, rusty ship in the
         middle of the ocean?!..^^
         
         When i admit this fact to myself, i try to remember what happend. Just yestarday evening, i was
         drinking in the company of my college friends, celebrating anniversary of graduation.. Then i went
         home. I can even remember how i went to bed! What on earth could've happend afterwards?!.."
         
         (list
             (ln "Look around"
                 (t "Anyway, i can't help myself just trying to remember what happend. It is in the past already
                    and i should think about future. I must learn my current situation and decide what to do
                    next.^^
                    With such thoughts, i started looking around. And suddenly i noticed that i'm not alone here!"))
             
             (ln "Keep trying to recall"
                 (t "I can't do anything without knoledge of situation. I must remember...^^
                    
                    There was this weird dream, about.. eh, elephants?.. Yeah, elephants; and i was riding one
                    of them. And when we came to our destination point, i awakend. I think i went to the kitchen
                    and drunk some water.. Was it my kitchen? Wait, was i really awake at that point? Or that
                    was just continuation of my dream?..^^
                    
                    Suddenly, my thoughts were interrupted by some sound. Like a human voice!..")))))

(bind "day0-1-investigate-ship"
      (t "After meeting each other, we decided to have a look at the ship. We need to find a way to contact
         somebody or to sail to some land.."))


(bindf "day0-2-found-control"
       (def story-stage :day0-2-found-control)
       (run (t "We've found some kind of control panel in this room.. However, it doesn't look very nice.
               In fact, it looks completly broken. Like somebody smashed it with some heavy object. And in
               some places it is even chopped by axe, which is lying of the floor here.^^
               
               What could've happen here?.. And, more importantly, is it possible to repair this control?
               Though this brings another question: whether engine itself is working or not..")))

(bindf "go-down"
       (change-map "cabins"))

(def inside-control-room false)

(bindf "go-control"
       (if inside-control-room
           (do (def inside-control-room false) (self-move 1 0))
           (do (def inside-control-room true) (self-move -1 0))))

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
