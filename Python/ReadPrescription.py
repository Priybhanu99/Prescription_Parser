from RegularExpressions import parse_mobile_number
from RegularExpressions  import parse_email
from PDFToText import convert_pdf_to_text

patient_data = {}

def prescription_parser(file_paths):
    prescription_path = file_paths
    final_text = ""
    for page in convert_pdf_to_text(prescription_path):
        final_text += ' ' + page
    
    text = final_text.split('\n')
    
    prescription_text = []

    for i in text:
        if i != ' ' and i != '':
            prescription_text.append(i)
    prescription_text[0]=prescription_text[0][2:]
    patient_data["Name"] = prescription_text[0] + ' ' + prescription_text[1]
    patient_data["Mobile"] = parse_mobile_number(final_text)
    patient_data["Email"] = parse_email(final_text)
    patient_data["Medicines"] = "Crocin"
    # print(patient_data)
    # print(prescription_text)
    # print(final_text)
    return patient_data

