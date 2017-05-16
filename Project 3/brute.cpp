#include <cstdlib>
#include <fstream>
#include <iostream>
#include <string>

#include "key.hpp"
#include "brute.hpp"

using namespace std;

std::string me;
std::string encrypted;
std::string table_filename;
bool verbose = false;

Brute::Brute(const std::string& filename) {
	T.resize(N);
	std::string buffer;
    std::fstream input(filename.c_str(), std::ios::in);
    for (int i = 0; i < N; i++) {
        std::getline(input, buffer);
        T[i].set_string(buffer);
    }
    input.close();
}

inline int getBit(unsigned long long int& i, int bit)
{
    return (i >> bit) & 0x01;
}

void Brute::decrypt(const std::string& encrypted){
	string s;
	for (int a = 0; a < C; a++) {
		s += "a";
	}
	
	encryptedKey.set_string(encrypted);
	
	s[s.size()-1] = 'b';
	one.set_string(s);
	increase.set_string(s);
	
	unsigned long long int pw = pow(2, N);
	for (unsigned long long int i = 0; i < pw; i++) {
		if (increase.subset_sum(T, false)  == encryptedKey) {
			cout << increase.toString() << endl;;
		}

		increase += one;
	}
}

void usage(const std::string& error_msg="") {
	if (!error_msg.empty()) std::cout << "ERROR: " << error_msg << '\n';
	std::cout << me << ": Brute force cracking of Subset-sum password with " 
		<< B << " bits precision\n"
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


int main(int argc, char *argv[]){
	
	initialize(argc, argv);
	Brute brute(table_filename);
	brute.decrypt(encrypted);
	
	return 0;
}
