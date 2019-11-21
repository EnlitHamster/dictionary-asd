#ifndef __MERGE_H__
#define __MERGE_H__

#include "../PreprocBooster.h"
#include PPB_PATH(PPB_DIR,PPB_FILE)

typedef int (*Compare)(void*, void*);
extern void merge(list_t* newList, list_t list1, list_t list2, Compare compareElems);

#endif
