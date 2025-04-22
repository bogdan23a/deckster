import { SvelteKitAuth } from "@auth/sveltekit";
import Auth0 from "@auth/sveltekit/providers/auth0";
import GitHub from "@auth/sveltekit/providers/github";

export const { handle, signIn, signOut } = SvelteKitAuth(async () => {
  return {
    trustHost: true,
    providers: [
      GitHub,
      Auth0
    ],
  }
})