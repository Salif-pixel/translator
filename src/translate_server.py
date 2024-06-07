from transformers import AutoTokenizer, MarianMTModel
import torch

def translate_text(text, src_lang, trg_lang):
    try:
        model_name = f"Helsinki-NLP/opus-mt-{src_lang}-{trg_lang}"
        # print(f"Loading model: {model_name}")
        model = MarianMTModel.from_pretrained(model_name)
        tokenizer = AutoTokenizer.from_pretrained(model_name)

         # print(f"Sample text: {text}")

        batch = tokenizer([text], return_tensors="pt")
        # print(f"Tokenized batch: {batch}")

        generated_ids = model.generate(**batch)
        # print(f"Generated IDs: {generated_ids}")

        translation = tokenizer.batch_decode(generated_ids, skip_special_tokens=True)[0]
        return translation

    except Exception as e:
        print(f"An error occurred: {e}")
        return None

if __name__ == "__main__":

    user_input = input("Enter the text you want to translate: ")
    src_lang = input("Enter the source language (e.g., 'fr' for French) or (e.g., 'en' for English):")
    trg_lang = input("Enter the target language (e.g., 'en' for English) or (e.g., 'fr' for French): ")
    translated_text = translate_text(user_input, src_lang, trg_lang)
    if translated_text:
        print(f"Translation: {translated_text}")
    else:
        print("Translation failed.")
