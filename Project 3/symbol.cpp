#include <cstdlib>
#include <fstream>
#include <iostream>
#include <string>
#include <unordered_map>

#include "key.hpp"
#include "symbol.hpp"

using namespace std;

std::string me;
std::string encrypted;
std::string table_filename;
bool verbose = false;

Symbol::Symbol(const std::string& filename) {
	T.resize(N);
	std::string buffer;
    std::fstream input(filename.c_str(), std::ios::in);
    for (int i = 0; i < N; i++) {
        std::getline(input, buffer);
        T[i].set_string(buffer);
    }
    input.close();
	
	// insert your code here
}

inline int getBit(unsigned long long int& i, int bit)
{
    return (i >> bit) & 0x01;
}

void Symbol::decrypt(const std::string& encrypted){
	
	string s = "";
	for (int a = 0; a < C; a++)
		s += "a";
	
	Key encrypt(encrypted);
	
	Key increase(s);
	int cutoff = floor(C / 2.0);
	s[cutoff-1] = 'b';
	Key one(s);
	unordered_map <string, vector<string>> m;
	unsigned long long int p = pow(2, 5 * cutoff);
	for (unsigned long long int i = 0; i < p; i++) {
		string key = (increase.subset_sum(T, false)).toString();
		string val = increase.toString();
		//cout << val << endl;
		auto k = m.find(key);
		if (k != m.end()) {
			k->second.push_back(val);
		} else {
			vector<string> v;
			v.push_back(val);
			pair<string, vector<string>> p(key, v);
			m.insert(p);
		}

		increase += one;
	}
	
	p = pow(2, 5 * (C - cutoff));
	s[cutoff-1] = 'a';
	increase.set_string(s);
	s[s.size()-1] = 'b';
	one.set_string(s);
	
	for (unsigned long long int i = 0; i < p; i++) {
		Key e2 = (increase.subset_sum(T, false));
		string val = increase.toString();
	
		string e1 = (encrypt.subtract(encrypt, e2, one.toString())).toString();
		
		auto k = m.find(e1);
		if (k != m.end()) {
			Key d1(k->second[0]);
			Key d2(val);
			cout << (d1 += d2).toString() << endl;;
			k->second.erase(k->second.begin());
		}

		increase += one;
	}
	
	
}

void usage(const std::string& error_msg="") {
	if (!error_msg.empty()) std::cout << "ERROR: " << error_msg << '\n';
	std::cout << me << ": Symbol table-based cracking of Subset-sum password"
		<< " with " << B << " bits precision\n"
	    << "USAGE: " << me << " <encrypted> <table file> [options]\n"
		<< "\nArguments:\n"
		<< " <encrypted>:   encrypted password to crack\n"
		<< " <table file>:  name of file containing the table to use\n"
		<< "\nOptions:\n"
		<< " -h|--help:     print this message\n"
		<< " -v|--verbose:  select verbose mode\n\n";
	exit(0);
}

void initialize(int argc, char* argv[]) {
	me = argv[0];
	if (argc < 3) usage("Missing arguments");
	encrypted = argv[1];
	table_filename = argv[2];
	for (int i=3; i<argc; ++i) {
		std::string arg = argv[i];
		if (arg == "-h" || arg == "--help") usage();
		else if (arg == "-v" || arg == "--verbose") verbose = true;
		else usage("Unrecognized argument: " + arg);
	}
}

int main(int argc, char *argv[]) {

	initialize(argc, argv);
	Symbol symbol(table_filename);
	symbol.decrypt(encrypted);
	
	return 0;
}
