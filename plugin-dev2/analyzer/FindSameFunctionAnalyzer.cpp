/*
 * FindSameFunctionAnalyzer.cpp
 *
 *  Created on: 2012-10-23
 *      Author: dandelion
 */

#include "include/FindSameFunctionAnalyzer.h"
#include "../core/include/ASTTreeRebuild.h"
#include "../core/include/TreePathWalker.h"
#include "../core/include/NodeProcessor.h"
#include "../core/include/ReportManager.h"
#include "../core/include/TotalProblem.h"
#include <sstream>
#include <map>
#include <fstream>
#include <cstdlib>
#include <string>
#include <iostream>
#include <string.h>
using std::cout;
using std::endl;
using std::map;
using std::string;
using std::stringstream;

FindSameFunctionAnalyzer* FindSameFunctionAnalyzer::mFindSameFunctionAnalyzer = NULL;

FindSameFunctionAnalyzer::FindSameFunctionAnalyzer() {
	mfunctionHashMap.clear();
}

FindSameFunctionAnalyzer::~FindSameFunctionAnalyzer() {
}

FindSameFunctionAnalyzer&
FindSameFunctionAnalyzer::getInstance()
{
	if(FindSameFunctionAnalyzer::mFindSameFunctionAnalyzer==NULL)
	{
		FindSameFunctionAnalyzer::mFindSameFunctionAnalyzer = new FindSameFunctionAnalyzer();
	}
	return *(FindSameFunctionAnalyzer::mFindSameFunctionAnalyzer);
}

void
FindSameFunctionAnalyzer::globalAnalyze(unsigned int hashCode, string function){
	     if (mfunctionHashMap.find(hashCode) != mfunctionHashMap.end()){
	    	 stringstream reportMsgStream;
	    	 					reportMsgStream<<"FindSameFunctionAnalyzer: " << filterString(mfunctionHashMap[hashCode]) << " maybe same as the " << filterString(function);
	    	 					string reportMsg = reportMsgStream.str();
	    	 					ReportManager::getInstance().insertReport(SrcManager::getInstance().getFullFileName(),0,reportMsg);
	    	 					ProblemList::GetInstance().Add((char*)"FindSameFunctionAnalyzer", SrcManager::getInstance().getFullFileName(), 0);

	     }else{
	    	 mfunctionHashMap[hashCode] = function;
	     	 	 }
}
string 
FindSameFunctionAnalyzer::filterString(string function){
    stringstream result;
    string s1 = "";
    string s2 = "";
    std::size_t found = function.find("$&$");
    if (found == std::string::npos){
     return "";   
    }
    s1 = function.substr(0, found);
    s2 = function.substr(found+3);
    found = s2.find(".printer");
    
    if (found == std::string::npos){
     return "";   
    }
    s2 = s2.substr(0, found);
    result << "function " << s2 << " in file " << s1 << ".c";
    return result.str();
}
