# BackStagepass

BackStagepass is a mobile booking companion for live music fans and talent bookers.  
It gives users a smooth way to:

- Explore a roster of artists
- Dive into beautiful artist profiles
- Request callbacks for bookings
- Manage their own profile inside the app

All built with a clean, modern UI and a focus on simplicity.

---

## Features

### Onboarding & Auth
- **Intro screen** with a branded BackStagepass logo and “Get Started” CTA.
- **Signup** screen:
  - Name, surname, username, email, password
  - Data stored locally via `SharedPreferences`
- **Login** screen:
  - Login with username + password
  - Validation and basic error messages
- Flow: Intro → Signup → Login → Roster (MainActivity).

### Artist Roster (MainActivity)
- **Top Artists** carousel:
  - Horizontal `RecyclerView` with gradient pill cards
  - High–quality artist images cropped into rounded rectangles
  - Tap any artist to open their **Detail** page
- **Genre row**:
  - Horizontal `RecyclerView` with cards styled like artist cards
  - Each genre has its own black-and-white hero image:
    - Amapiano → waffles_uncle.jpg  
    - Hip-Hop → maglera_.jpg  
    - RnB → tyla_pink.jpg
  - Tapping a genre filters the top artists by that genre.
- **Search bar**:
  - Type to filter the top artists by name
  - Works together with the genre filter

### Artist Detail (DetailActivity)
- Hero layout with:
  - Blurred full-screen background image
  - Center artist image in rounded card
  - Artist name and genre
- **Info card** with quick stats (for supported artists), e.g.:
  - Spotify monthly listeners
  - Instagram, TikTok, YouTube followers/subscribers
- **Back arrow** in the top bar to return to the roster.
- **“Book Artist”** button pinned to the bottom.

### Booking Requests (BookingsActivity)
- Pre-fills the selected artist’s name from the detail screen.
- Form fields:
  - Event date
  - Event location
  - Event type (Concert, Festival, Corporate Event)
- **Request Callback** button:
  - Reads user email from `SharedPreferences`
  - Opens an email app with a pre-composed callback request

### User Profile (ProfileActivity)
- Background with crowd image and circular logo/photo ring.
- Profile fields:
  - Profile image (pick from gallery)
  - Username
  - Email
  - Phone number
  - Role spinner (Booking Manager, Talent Booker, Artist Manager)
- Data is saved locally with `SharedPreferences`.
- Back arrow (in the app bar/back navigation) returns to the previous screen.

### Navigation
- **Bottom bar** on MainActivity:
  - Profile icon → opens [ProfileActivity](cci:2://file:///c:/Users/user/BackStagepass%20%281%29/BackStagepass2/app/src/main/java/com/example/backstagepass/ProfileActivity.kt:13:0-110:1)
  - Home/roster icon → scrolls roster to top
  - Bookings icon → opens [BookingsActivity](cci:2://file:///c:/Users/user/BackStagepass%20%281%29/BackStagepass2/app/src/main/java/com/example/backstagepass/BookingsActivity.kt:12:0-88:1)
- Back arrows (or system back) return to previous screen throughout the app.


- **Language**: Kotlin
- **UI**: Android Views + ConstraintLayout
- **Architecture**: Single-module Android app
- **Libraries / Components**:
  - AndroidX AppCompat
  - Material Components (`ShapeableImageView`, BottomAppBar, FAB)
  - RecyclerView

- **Storage**:
  - `SharedPreferences` for:
    - Auth credentials (username, email, password)
    - Profile data (name, email, phone, role, image URI)


---

