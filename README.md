1. **Project Title**
   PC Parts Compatibility Checker

2. **Team Members**
   Jules B. Bautista
   Gabrielle C. Querijero

4. **Problem Statement & Goals**
   Building custom PC requires that all hardware components are compatible with one another. Beginners and even experienced builders may accidentally use parts that do not work together. Processors have their own sockets and power supplies should have an allowance to support the total power requirement of the PC.

   The goal of this application is to simplify the PC building process by allowing users to freely choose their own components and verify their compatibility. The system would also estimate the total cost and power consumption of the build.

6. **Target User**
   The primary users of the application are first time PC builders, gamers, computer engineering students, and computer retail shops that assist costumers in creating their own PC build.

7. **Brief Description**
   The PC part compatibility checker is a desktop application that allows user to freely choose and create their own computer build by selecting components such as the processor, motherboard, graphics card, RAM, storage, and many other components. As the components are selected the application verifies wheter they are compatible based on the specification of the component. The application also computes for the estimated total cost and power consumption, then generates a report where the user will see the compatibility report and suggestions on the build before the user finalizes.

8. **Core OOP Concepts**
   - Encapsulation - 
   - Inheritance - 
   - Polymorphism - 
   - Abstraction - 

9. **Initial Class Ideas**
   - `Component` - Base class containing model, brand, price, and power consumption
   - `Processor` - Stores socket type, core count, thermal design power (TDP).
   - `Motherboard` - Stores socket type, chipset, RAM support, and form factor.
   - `RAM` - Memory module that includes capacity, speed, CAS (Column Address Strobe) Latency (CL)
   - `GraphicsCard` - Stores VRAM, power requirements
   - `Storage` - Represents storage devices such as SSDs and HDDs with capacity and speed information
   - `Power Supply` - Contains wattage, efficiency rating, and power-relating specifications
   - `Case` - Stores compatible motherboard form factor and physical dimensions
   - `Build` - Maintains the collection of all components selected and calculates total price and estimated power consumption
   - `CompatibilityChecker` - Validates if components are compatible and generates report identifying any conflicts and suggestions.

   

### Recommended Additions (Strengthen Your Proposal)

8. **User Stories**
   Short feature descriptions from the end-user's perspective:
   > "As a store owner, I want to receive a low-stock alert so that I can reorder before running out."

9. **Core Features**
   A bulleted list of the main functions your system will support (e.g., user login, inventory tracking, sales reporting, receipt generation).

---

## 4. README.md Template

```
PROJECT TITLE:
<Your project name>

TEAM MEMBERS:
<Name 1> - <GitHub username>
<Name 2> - <GitHub username>
<Name 3> - <GitHub username>
(or: Solo Project - <Name> - <GitHub username>)

PROBLEM STATEMENT & GOALS:
<What problem does this solve? What are the main objectives?>

TARGET USER:
<Who will use this system?>

BRIEF DESCRIPTION:
<Summary of purpose and basic functionality>

CORE OOP CONCEPTS:
- Encapsulation: <where/how>
- Inheritance: <where/how>
- Polymorphism: <where/how>
- Abstraction: <where/how>

INITIAL CLASS IDEAS:
- ClassName1: <responsibility>
- ClassName2: <responsibility>
- ClassName3: <responsibility>

USER STORIES (Recommended):
- As a <user type>, I want to <action> so that <goal>.
- As a <user type>, I want to <action> so that <goal>.

CORE FEATURES (Recommended):
- <Feature 1>
- <Feature 2>
- <Feature 3>
```

---

## 5. Evaluation Checklist Before You Start Coding

- [ ] Repository created, all members added as collaborators
- [ ] `README.md` completed with all required sections
- [ ] `.gitignore` added
- [ ] Branching strategy agreed upon (`main` + feature branches)
- [ ] Each member has made at least one initial commit under their own account

**Reminder:** Since group contribution is assessed through GitHub activity, an incomplete or GitHub-inactive member will be graded **zero on the project**, even if the team submits a working application. Set up your workflow so that everyone's participation is visible from the start - not reconstructed at the deadline.

## Example Picture (if applicable):

```
![Test Image](https://i.imgur.com/RcVBZeD.jpeg)
```

![Test Image](https://old.dlsu.edu.ph/wp-content/uploads/2018/08/dlsu_logo.gif)
