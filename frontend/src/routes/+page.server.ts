import { saveDeck } from '$lib/server/db';
import { redirect } from '@sveltejs/kit';

import type { PageServerLoad, PageServerLoadEvent } from "./$types";
import { getDecksPage } from "$lib/server/db";

export const load: PageServerLoad = async (event: PageServerLoadEvent) => {
  return {
    session: await event.locals.auth(),
    decks: await getDecksPage()
  };
};
