import os
import ReadPrescription


prescription_list = []

for prescription in os.listdir("./prescriptions"):
    prescription_list.append("/home/prabhsimar100/Documents/project/prescriptions/" + prescription)

for prescription_path in prescription_list:
    print(prescription_path)
    print(ReadPrescription.prescription_parser(prescription_path))




