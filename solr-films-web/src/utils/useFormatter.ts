export const useFormatter = function () {
	const dateTimeFormatter = new Intl.DateTimeFormat('ru-RU');

	function formatDate(src: string) {
		const date = new Date(src);
		if (!isNaN(date.getTime())) {
			return dateTimeFormatter.format(date);
		}
		return src;
	}
	return { formatDate };
};
