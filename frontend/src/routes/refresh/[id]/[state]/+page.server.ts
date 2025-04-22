import type { PageServerLoad } from "./$types";

export const load: PageServerLoad = async (event) => {
    let { params } = event;
    return {
        params
    }
}