Feature: Fonctionnalités de la fiche meetup

  Scenario: Vérification du Header
    Given je suis sur une page meetup
    Then le titre doit etre cliquable avec le nom du meetup
    And un lieu
    And le total des membres
    And les organisateurs
    And un bouton avec un label "Rejoindre ce groupe"
    And une photo de presentation.
