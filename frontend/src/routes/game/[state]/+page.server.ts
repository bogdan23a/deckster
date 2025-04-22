import type { PageServerLoad } from "./$types";
import { createNewGame } from "$lib/server/db";
import { redirect } from "@sveltejs/kit";

export const load: PageServerLoad = async (event) => {
  let session = await event.locals.auth();
  let game = await createNewGame(session?.user?.email || '');
  redirect(303, `/game/${game.id}/DRAFT`);
}