# Final Project: GitHub Repository Initiation Guidelines

These guidelines walk your team (or you, if going solo) through **setting up your Final Project GitHub repository correctly from day one**, and specify exactly what your `README.md` proposal must contain. Follow this before writing a single line of application code.

---

## 1. Why This Matters

Per the Final Project Policy:
- Your project must solve a **real-world problem** and be **creative, original work**.
- It must be **functional** and demonstrate **core OOP concepts, code conventions, and GUI design**.
- Groups are **at most 3 members** (solo is allowed).
- **If working in a group, contributions are evaluated based on GitHub activity.** Members with no visible contribution (commits, PRs, issues, etc.) will receive a **project grade of zero**, regardless of the team's overall output.

This means the repository itself is part of your grade: not just the code inside it.

---

## 2. Step-by-Step: Initiating the Repository

1. **Create the repository**
   - One member creates a **new GitHub repository** (public or private -- private is fine, just add your instructor as a collaborator).
   - Repository name convention: `LBYCPOB-FinalProject-<ShortProjectName>` (adjust prefix to your actual course code).

2. **Add all team members as collaborators**
   - Settings → Collaborators → Add each member by GitHub username.
   - Every member must have **push access** - no single-owner workflows where one person commits on behalf of others.

3. **Initialize with the essentials**
   - `README.txt` (proposal - see Section 3 below)
   - `.gitignore` (appropriate for your language/framework, e.g., Java/Maven, Python, Node)
   - `LICENSE` (optional, but good practice for "original work" framing)

4. **Set up branching from the start**
   - `main` branch stays stable/working at all times.
   - Each member works in **feature branches** (`feature/login-ui`, `feature/inventory-class`, etc.) and merges via **Pull Requests**.
   - This is what makes individual contributions visible and attributable.

5. **Adopt a commit convention immediately**
   - Write descriptive commit messages tied to what changed and why, e.g.:
     `Add Encapsulation to Account class; hide balance field behind getters/setters`
   - Avoid vague messages like `update`, `fix`, `final`.

6. **Use GitHub Issues or a Projects board**
   - Log tasks/features as Issues, assign them to members, and close them via linked commits/PRs.
   - This creates a visible paper trail of who did what - directly supporting the contribution-based grading policy.

7. **Commit early, commit often, individually**
   - Each member should have commits under their **own GitHub account** from the first week onward.
   - Irregular, one-time, end-of-term commit dumps are a red flag during evaluation and will not be treated as genuine contribution.

---

## 3. `README.txt`: Required Proposal Elements

Your `README.txt` (placed at the root of the repo) is your **project proposal**. It must contain the following, in order:

### Required Sections

1. **Project Title**
   A clear, descriptive name for your project.

2. **Team Members**
   Full names and GitHub usernames of all members (or "Solo Project" with your name).

3. **Problem Statement & Goals**
   - What real-world problem are you solving?
   - What are the main objectives of your application?

4. **Target User**
   Who will actually use this system? Be specific (e.g., "small sari-sari store owners," "university lab instructors," not just "people").

5. **Brief Description**
   A short paragraph summarizing the project's purpose and basic functionality - what it does, end to end.

6. **Core OOP Concepts**
   List the OOP principles you intend to apply and *where* they'll show up, e.g.:
   - Encapsulation - private fields with controlled access in `Account`
   - Inheritance - `Employee` → `Manager`, `Cashier`
   - Polymorphism - overridden `calculatePay()` per employee type
   - Abstraction - `PaymentMethod` interface implemented by `CashPayment`, `GCashPayment`

7. **Initial Class Ideas**
   Identify your main classes (the "nouns" of your problem domain) and their basic responsibilities, e.g.:
   - `InventoryItem` - holds product data, stock level
   - `Order` - aggregates items, computes totals
   - `User` - authentication and role management

### Recommended Additions (Strengthen Your Proposal)

8. **User Stories**
   Short feature descriptions from the end-user's perspective:
   > "As a store owner, I want to receive a low-stock alert so that I can reorder before running out."

9. **Core Features**
   A bulleted list of the main functions your system will support (e.g., user login, inventory tracking, sales reporting, receipt generation).

---

## 4. README.txt Template

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
- [ ] `README.txt` completed with all required sections
- [ ] `.gitignore` added
- [ ] Branching strategy agreed upon (`main` + feature branches)
- [ ] Each member has made at least one initial commit under their own account

**Reminder:** Since group contribution is assessed through GitHub activity, an incomplete or GitHub-inactive member will be graded **zero on the project**, even if the team submits a working application. Set up your workflow so that everyone's participation is visible from the start - not reconstructed at the deadline.

## Example Picture:

![Test Image](https://raw.githubusercontent.com/melvincabatuan/LBYCPOB-FinalProject-ShortProjectName/blob/master/images/flag.png)
