import { getDecksPage } from "$lib/server/db";
import type {PageServerLoad} from "./$types";


export const load: PageServerLoad = async (event: { locals: { auth: () => any; }; }) => {
	console.log(event.locals.auth());
	return {
		session: await event.locals.auth(),
		decks: await getDecksPage()
	};
};