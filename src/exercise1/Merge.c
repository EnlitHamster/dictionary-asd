#include "Merge.h"

void merge(list_t* newList, list_t list1, list_t list2, Compare compare_elems)
{
	if (newList != NULL && *newList != NULL && is_list_empty(*newList) && list1 != NULL && list2 != NULL && compare_elems != NULL)
	{
		iterator_t it1 = list_iterator(list1);
		iterator_t it2 = list_iterator(list2);

		while (is_iterator_valid(it1) && is_iterator_valid(it2))
		{
			if (compare_elems(iterator_get(it1), iterator_get(it2)) <= 0)
			{
				list_add(*newList, iterator_get(it1));
				iterator_next(it1);
			}
			else
			{
				list_add(*newList, iterator_get(it2));
				iterator_next(it2);
			}
		}

		while (is_iterator_valid(it1))
		{
			list_add(*newList, iterator_get(it1));
			iterator_next(it1);
		}

		while (is_iterator_valid(it2))
		{
			list_add(*newList, iterator_get(it2));
			iterator_next(it2);
		}
	}
}
