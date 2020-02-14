# NavigationComponentsTest
Proof of concept app to test out Android's navigation library and a design pattern for a first time onboarding experience

## High level overview
This is a POC to flesh out how onboarding on Runkeeper could exist given the current business requirements for navigation. This app leverages ViewModel and the Navigation Components for business logic and navigation. The design is kind of a reactive MVVM where the different components largely interact with each other via rx Observables. The events emitted by the Observables are instances of sealed classes that dictate behavior.

## Major Players

### MainActivity
The main activity for the app. This kicks off the onboarding activity. When onboarding completes, this activity is resumed.

### OnboardingActivity
The main onboarding activity that hosts the nav graph for all the various nav states. The nav graph automatically places the first destination fragment defined in the graph.
This activity is responsible for initializing the `OnboardingViewModel` and subscribing to it for when onboarding completes.

### nav_graph
The xml file containing all the destinations for onboarding. There are references to all of the fragments involved and actions representing all possible state transitions that can occur during onboarding.

### OnboardingViewModel
This is the ViewModel class that is shared by all of the onboarding states. This is accessible by the host onboarding activity and by the individual onboarding fragments. It contains state required by the individual nav states to determine the next nav state. 
There's one important method here `markCurrentOnboardingState(OnboardingNavState)`. This method observes on the `navEvents` Observable to execute navigation.
This view model also contains an `onboardingExitEvent` Single that emits an item once onboarding is complete.

### OnboardingNavEvent
This is the kind of event that each instance of OnboardingNavState emits. This indicates that a navigation is required. Here are the types of nav events:
#### OnboardingNavBack
This is a navigation request to go back with the back button. This event has a `root` parameter that's a flag to indicate whether we should exit onboarding as a result of this back navigation.
##### OnboardingNavForward
This is to navigate forward. This event has a `navTransition`, which is an instance of `NavDirections`. This will tell the `NavigationHelper` where to navigate to.
#### OnboardingNavComplete
This marks that onboarding is complete

#### OnboardingNavState
This interface represents each step of onboarding. All of the individual onboarding fragments create an instance of this. This contains a property `navEvents` of type `Single<OnboardingNavEvent>`. The instance of `OnboardingNavState` needs to emit nav events for when the user decides to move forward or back.

#### NavigationHelper
This is just a wrapper interface around Android's `NavController`.
